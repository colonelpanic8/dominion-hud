(ns dominion-hud.content-script
  (:require-macros [chromex.support :refer [runonce]])
  (:require [cljs.core.async :refer [<!]]
            [chromex.logging :refer-macros [log info warn error group group-end]]
            [chromex.protocols :refer [post-message!]]
            [chromex.ext.runtime :as runtime :refer-macros [connect]]))

(runonce
 (log "AWESOME"))
