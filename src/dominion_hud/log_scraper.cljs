(ns dominion-hud.log-scraper)

(defn get-log-lines []
  (let [log-lines (.getElementsByClassName js/document "log-line")]
    (map #(.-innerText %) (array-seq log-lines))))


