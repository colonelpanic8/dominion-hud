(ns dominion-hud.popup
  (:require-macros [chromex.support :refer [runonce]])
  (:require [dominion-hud.popup.core :as core]))

(runonce
  (core/init!))
