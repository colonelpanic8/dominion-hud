(ns dominion-hud.test
  (:require [cljs.test :refer-macros [run-all-tests]]
            [dominion-hud.test.deck-tracker]))

(enable-console-print!)

(defn ^:export run []
  (run-all-tests))

(run)
