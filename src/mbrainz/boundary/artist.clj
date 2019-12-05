(ns mbrainz.boundary.artist
  (:require [mbrainz.boundary.core :as core]))

(defn get-artist-by-gid
  ([db gid] (get-artist-by-gid db gid '[*]))
  ([db gid pull-expr]
   {:pre [(uuid? gid)]}
   (core/pull db {:selector pull-expr
                  :eid [:artist/gid gid]})))
