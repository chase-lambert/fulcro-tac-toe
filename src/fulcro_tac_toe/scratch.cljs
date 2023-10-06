(ns fulcro-tac-toe.scratch
  (:require 
    [com.fulcrologic.fulcro.application :as app]
    [com.fulcrologic.fulcro.components :as comp :refer [defsc]]
    [com.fulcrologic.fulcro.dom :as dom]
    [com.fulcrologic.fulcro.react.version18 :refer [with-react18]]
    [com.fulcrologic.fulcro.mutations :as m :refer [defmutation]]))


(def squares
  (mapv (fn [n]
          {:square/id n
           :value nil})
        (range 1 10)))
      
(def square 
  (nth squares 0))
      
(assoc square :value "X")
