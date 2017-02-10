(ns lein-test-report-junit-xml.plugin
  (:require [lein-test-report.utils :refer [add-profile]]))

(defn middleware [project]
  (let [output-dir (or (System/getenv "TEST_REPORT_JUNIT_XML_OUTPUT_DIR")
                       (-> project :test-report-junit-xml :output-dir)
                       "target/test-reports")
        options (-> project
                    (:test-report-junit-xml {})
                    (dissoc :output-dir))]
    (add-profile project {:dependencies [['test-report-junit-xml "0.1.0-SNAPSHOT"]]
                          :plugins [['lein-test-report "0.1.0"]]
                          :injections `[(require 'test-report-junit-xml.core)
                                        (require 'clojure.java.io)]
                          :test-report {:summarizers `[#(test-report-junit-xml.core/write ~output-dir % ~options)]}})))
