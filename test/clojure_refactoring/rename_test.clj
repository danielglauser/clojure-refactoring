(ns clojure-refactoring.rename-test
  (:use clojure.test)
  (:use clojure.contrib.mock)
  (:use clojure-refactoring.rename :reload))

(def a nil)

(deftest renames-basic-function-call
  (is (= (rename '(defn a [b] (+ b 1)) 'a 'c)
         "(defn c [b] (+ b 1))\n"))
  (is (= (rename '(defn c [d] (+ d 2)) 'c 'z)
         "(defn z [d] (+ d 2))\n")))

(deftest renames-recursive-function-call
  (is (= (rename '(defn f [n] (if (<= n 1) 1 (f (dec n)))) 'f 'fact)
         "(defn fact [n] (if (<= n 1) 1 (fact (dec n))))\n")))

(deftest renaming_fn
  (testing "it replaces occurences of the var name"
    (is (= ((renaming-fn #'a 'z) '(defn b [c] (a 1 2)))
           '(defn b [c] (z 1 2)))))) ;;eventually, this should only
;;replace things that resolve to vars in that namespace