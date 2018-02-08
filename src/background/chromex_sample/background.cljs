(ns dominion-hud.background
  (:require-macros [chromex.support :refer [runonce]])
  (:require [dominion-hud.background.core :as core]))

(runonce
  (core/init!))
