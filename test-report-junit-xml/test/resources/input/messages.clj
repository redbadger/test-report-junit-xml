[{:type :begin-test-ns
  :ns (find-ns 'example.first-test)
  :time 51705939435917}
 {:type :begin-test-var
  :var #'example.first-test/nested
  :time 51705941156806}
 {:type :pass
  :expected '(= 0 0)
  :actual (list = 0 0)
  :message nil
  :time 51705942160982
  :context (list "inner" "outer")}
 {:type :end-test-var
  :var #'example.first-test/nested
  :time 51705942255394}
 {:type :begin-test-var
  :var #'example.first-test/passing
  :time 51705942304461}
 {:type :pass
  :expected '(= 0 0)
  :actual (list = 0 0)
  :message nil
  :time 51705942641504
  :context ()}
 {:type :pass
  :expected '(= 0 0)
  :actual (list = 0 0)
  :message nil
  :time 51705942719891
  :context ()}
 {:type :end-test-var
  :var #'example.first-test/passing
  :time 51705942761235}
 {:type :begin-test-var
  :var #'example.first-test/erroring
  :time 51705942804163}
 {:file "Numbers.java"
  :line 158
  :type :error
  :expected '(= 0 (/ 0 0))
  :actual (doto (ArithmeticException. "Divide by zero")
            (.setStackTrace (into-array [(StackTraceElement. "clojure.lang.Numbers" "divide" "Numbers.java" 158)
                                         (StackTraceElement. "clojure.lang.Numbers" "divide" "Numbers.java" 3808)
                                         (StackTraceElement. "example.first_test$fn__1305" "invokeStatic" "first_test.clj" 17)
                                         (StackTraceElement. "example.first_test$fn__1305" "invoke" "first_test.clj" 16)])))

  :message nil
  :time 51705943130574
  :context ()}
 {:type :end-test-var
  :var #'example.first-test/erroring
  :time 51705943173760}
 {:type :begin-test-var
  :var #'example.first-test/failing
  :time 51705943223768}
 {:file "first_test.clj"
  :line 14
  :type :fail
  :expected '(= 0 1)
  :actual '(not (= 0 1))
  :message nil
  :time 51705943734108
  :context ()}
 {:type :end-test-var
  :var #'example.first-test/failing
  :time 51705943780352}
 {:type :end-test-ns
  :ns (find-ns 'example.first-test)
  :time 51705943839193}
 {:type :begin-test-ns
  :ns (find-ns 'example.second-test)
  :time 51705943913222}
 {:type :begin-test-var
  :var #'example.second-test/passing
  :time 51705944232368}
 {:type :pass
  :expected '(= 0 0)
  :actual (list = 0 0)
  :message nil
  :time 51705944438664
  :context ()}
 {:type :end-test-var
  :var #'example.second-test/passing
  :time 51705944538554}
 {:type :end-test-ns
  :ns (find-ns 'example.second-test)
  :time 51705944585988}
 {:test 5
  :pass 0
  :fail 0
  :error 0
  :type :summary
  :time 51705944686193}]
