(ns fulcro-tac-toe.scratch
  (:require 
    [com.fulcrologic.fulcro.application :as app]
    [com.fulcrologic.fulcro.components :as comp :refer [defsc]]
    [com.fulcrologic.fulcro.dom :as dom]
    [com.fulcrologic.fulcro.react.version18 :refer [with-react18]]
    [com.fulcrologic.fulcro.mutations :as m :refer [defmutation]]))

(def winning-lines
  [[0 1 2]
   [3 4 5]
   [6 7 8]
   [0 3 6]
   [1 4 7]
   [2 5 8]
   [0 4 8]
   [2 4 6]])

(def squares
  [#:square{:id 0, :value "X"}
   #:square{:id 1, :value "X"}
   #:square{:id 2, :value "X"}
   #:square{:id 3, :value nil}
   #:square{:id 4, :value nil}
   #:square{:id 5, :value nil}
   #:square{:id 6, :value nil}
   #:square{:id 7, :value nil}
   #:square{:id 8, :value nil}],)
      
(def square 
  (nth squares 0))

(defn winner? [squares]
  (first
    (for [[a b c] winning-lines
          :let [row [(nth squares a) 
                     (nth squares b) 
                     (nth squares c)]
                [d e f] (map :square/value row)]
          :when (and d e f (= d e f))]
      d))) 

(winner? squares)

(def winning-lines
  [[0 1 2]
   [3 4 5]
   [6 7 8]
   [0 3 6]
   [1 4 7]
   [2 5 8]
   [0 4 8]
   [2 4 6]])
      
(assoc square :value "X")
