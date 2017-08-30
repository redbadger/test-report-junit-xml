(ns test-report-junit-xml.core
  (:require [test-report.options :refer [with-options]]
            [test-report.summary :refer [summarize]]
            [clojure.data.xml :as xml]
            [clojure.java.io :as io]
            [clojure.stacktrace :as stacktrace]
            [clojure.string :as string]))

(defmulti ^:private format-result :type)

(defn- format-stacktrace [error]
  (with-out-str (stacktrace/print-cause-trace error)))

(def ^:dynamic *format-result* format-result)
(def ^:dynamic *format-stacktrace* format-stacktrace)

(defn- join-non-blanks [delimiter & strings]
  (->> strings (remove string/blank?) (string/join delimiter)))

(defn- context [result]
  (->> result :context reverse (string/join " ")))

(defn- error-message [error]
  (when (instance? Throwable error)
    (.getMessage error)))

(defn- error-cause [error]
  (if (instance? Throwable error)
    (*format-stacktrace* error)
    (prn-str error)))

(defmethod format-result :fail [result]
  (let [message (join-non-blanks ": " (context result) (:message result))]
    {:tag :failure
     :attrs {:message message}
     :content (join-non-blanks "\n"
                               message
                               (str "expected: " (-> result :expected prn-str)
                                    "  actual: " (-> result :actual prn-str)
                                    "      at: " (:file result) ":" (:line result)))}))

(defmethod format-result :error [result]
  (let [message (join-non-blanks ": " (context result) (:message result) (-> result :actual error-message))]
    {:tag :error
     :attrs {:message message}
     :content (join-non-blanks "\n"
                               message
                               (str "expected: " (-> result :expected prn-str)
                                    "  actual: " (-> result :actual error-cause)))}))

(defmethod format-result :default [result])

(defn- nanos->secs [nanos]
  (->> nanos (* 1e-9) (format "%.3g")))

(defn- testcase [test-var]
  (let [var-meta (-> test-var :var meta)]
    {:tag :testcase
     :attrs {:classname (-> var-meta :ns ns-name)
             :name (:name var-meta)
             :time (-> test-var :time nanos->secs)}
     :content (keep *format-result* (:results test-var))}))

(defn- testsuite [test-ns]
  (let [counts (:summary test-ns)]
    {:tag :testsuite
     :attrs {:name (-> test-ns :ns ns-name)
             :tests (:test counts)
             :assertions (:assertion counts)
             :errors (:error counts)
             :failures (:fail counts)
             :time (-> test-ns :time nanos->secs)}
     :content (map testcase (:tests test-ns))}))

(defn- output-file [output-dir testsuite]
  (io/file output-dir (str "TEST-" (-> testsuite :attrs :name) ".xml")))

(defn- write-xml-files [output-dir testsuites]
  (.mkdirs (io/file output-dir))
  (doseq [testsuite testsuites]
    (with-open [writer (io/writer (output-file output-dir testsuite))]
      (xml/emit testsuite writer))))

(defn write
  "Writes a JUnit-XML-formatted summary of the given clojure.test/report
  messages to the given output directory.

  Output may be configured by supplying the following options (or by binding the
  corresponding dynamic vars):
  :format-result     - a function that converts a test result message into an
                       XML element (a map with keys [:tag :attrs :content]), or
                       nil if no element should be output (e.g. if the message
                       :type is :pass)
                       (default test-report-junit-xml.core/format-result)
  :format-stacktrace - a function that takes a Throwable and returns a string
                       containing the formatted stacktrace (may not be used if
                       :format-result is configured)
                       (default test-report-junit-xml.core/format-stacktrace,
                       uses clojure.stacktrace/print-cause-trace)"
  ([output-dir messages] (write output-dir messages {}))
  ([output-dir messages options]
   (with-options options
     (->> messages
          summarize
          :namespaces
          (map testsuite)
          (write-xml-files output-dir)))))
