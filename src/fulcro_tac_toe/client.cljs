(ns fulcro-tac-toe.client
  (:require
   [com.fulcrologic.fulcro.algorithms.merge :as merge]
   [com.fulcrologic.fulcro.application :as app]
   [com.fulcrologic.fulcro.components :as comp :refer [defsc]]
   [com.fulcrologic.fulcro.dom :as dom]
   [com.fulcrologic.fulcro.mutations :as m :refer [defmutation]]
   [com.fulcrologic.fulcro.react.version18 :refer [with-react18]]))

(defonce APP 
  (with-react18 (app/fulcro-app)))

(comment
  (keys APP)
  (-> APP (::app/state-atom) deref))

(def winning-lines
  [[0 1 2]
   [3 4 5]
   [6 7 8]
   [0 3 6]
   [1 4 7]
   [2 5 8]
   [0 4 8]
   [2 4 6]])

(def initial-board
  #:board{:id 1
          :squares (mapv (fn [n]
                          {:square/id n
                           :square/value nil})
                         (range 9))
          :winner false
          :turn "X"}) 

(defmutation claim-square [{:square/keys [id value] :as props}]
  (action [{:keys [state]}]
    (let [current-turn (get-in (:board/id @state) [1 :board/turn])]
      (when (nil? value)
        (swap! state assoc-in [:square/id id :square/value] current-turn)
        (swap! state assoc-in [:board/id 1 :board/turn] (if (= current-turn "X") "O" "X"))))))

(defsc Square [this {:square/keys [value] :as props}]
  {:query [:square/id :square/value]
   :ident :square/id}
  (dom/button :.square {:aria-label "square button"
                        :onClick (fn [] (comp/transact! this [(claim-square props)]))}
    value))

(def ui-square (comp/factory Square {:keyfn :square/id}))

(defsc Board [_ {:board/keys [squares winner turn]}]
  {:query [:board/id :board/turn :board/winner {:board/squares (comp/get-query Square)}]
   :ident :board/id}
  (let [[row-1 row-2 row-3] (partition 3 squares)]
    (comp/fragment
      (dom/h3 :.status "Next player is: " turn)
      (when winner (dom/h1 "Winner!"))
      (dom/div :.board-row (map ui-square row-1))
      (dom/div :.board-row (map ui-square row-2))
      (dom/div :.board-row (map ui-square row-3)))))
      
(def ui-board (comp/factory Board {:keyfn :board/id}))

(defsc Root [_ {:root/keys [board]}]
  {:query [{:root/board (comp/get-query Board)}]
   :initial-state (fn [_ _] {:root/board initial-board})}
  (dom/div 
    (ui-board board)))

(comment
  (app/current-state APP)
  (app/schedule-render! APP)
  ,) 
  

(defn ^:export init []
  (app/mount! APP Root "app")
  (js/console.log "Loaded"))

(defn ^:export refresh []
  (app/mount! APP Root "app")
  (comp/refresh-dynamic-queries! APP)
  (js/console.log "Hot reload"))

