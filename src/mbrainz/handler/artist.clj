(ns mbrainz.handler.artist
  (:require [ataraxy.response :as response]
            [integrant.core :as ig]
            [mbrainz.boundary.artist :as boundary]))

(defmethod ig/init-key ::fetch [_ {:keys [db]}]
  (fn [{[_ gid] :ataraxy/result}]
    (when-let [m (boundary/get-artist-by-gid db gid)]
      [::response/ok m])))
