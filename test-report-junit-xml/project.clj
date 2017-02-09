(defproject test-report-junit-xml "0.1.0-SNAPSHOT"
  :description "Library providing JUnit XML output for clojure.test"
  :url "https://github.com/redbadger/test-report-junit-xml"
  :scm {:dir ".."}
  :license {:name "Eclipse Public License", :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/data.xml "0.2.0-alpha2"]
                 [test-report "0.1.0"]]
  :profiles {:test {:resource-paths ["test/resources"]}})
