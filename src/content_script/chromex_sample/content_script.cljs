(ns dominion-hud.content-script
  (:require-macros [chromex.support :refer [runonce]])
  (:require [dominion-hud.content-script.core :as core]))

(runonce
  (core/init!))
