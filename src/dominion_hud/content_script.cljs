(ns dominion-hud.content-script
  (:require-macros [chromex.support :refer [runonce]])
  (:require [chromex.logging :refer-macros [log info warn error group group-end]]
            [dominion-hud.log-scraper :refer [get-log-lines]]))

(runonce
 (log "AWESOME"))
