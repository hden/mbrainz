(ns mbrainz.lambda.artist
  (:require [clojure.data.json :as json]
            [mbrainz.boundary.artist :as boundary]
            [mbrainz.edn :as edn]
            [mbrainz.lambda.core :as core]))

(defn get-artist-by-gid
  "Lambda ion that returns the artist by gid."
  [{:keys [input]}]
  (let [gid (-> input
                json/read-str
                core/uuid-from-string)]
    (-> (core/get-connection)
        (boundary/get-artist-by-gid gid)
        (edn/write-str))))
