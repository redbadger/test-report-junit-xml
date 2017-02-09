(ns test-report-junit-xml.core-test
  (:require [clojure.test :refer :all]
            [test-report-junit-xml.core :refer :all]
            [clojure.java.io :as io]))

(create-ns 'example.first-test)
(intern 'example.first-test 'passing (fn []))
(intern 'example.first-test 'failing (fn []))
(intern 'example.first-test 'erroring (fn []))
(intern 'example.first-test 'nested (fn []))
(create-ns 'example.second-test)
(intern 'example.second-test 'passing (fn []))

(def slurp-resource (comp slurp io/file io/resource))

(deftest produce-xml
  (let [input (-> "input.clj" slurp-resource read-string eval)
        output (slurp-resource "output.xml")]
    (is (= (slurp-resource "output.xml") (with-out-str (write *out* input))))))
