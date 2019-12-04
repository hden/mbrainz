#!/usr/bin/env bb

(defn get-url [url]
  (println "Fetching url:" url)
  (let [{:keys [exit err out]} (shell/sh "curl" "-sS" url)]
    (if (zero? exit)
      out
      (do (println "ERROR:" err)
          (System/exit 1)))))

(defn write-edn [content file]
  (println "Writing file:" file)
  (spit file (pr-str {:tx-data content})))

(def base-url "https://raw.githubusercontent.com/Datomic/mbrainz-importer/master/subsets/")
(def base-path "resources/mbrainz/migrations/")
(def files [["01" "batches"  "schema.edn"]
            ["02" "batches"  "enums.edn"]
            ["03" "entities" "countries.edn"]
            ["04" "batches"  "artists.edn"]
            ["05" "batches"  "areleases.edn"]
            ["06" "batches"  "areleases-artists.edn"]
            ["07" "batches"  "labels.edn"]
            ["08" "entities" "scripts.edn"]
            ["09" "entities" "langs.edn"]
            ["10" "batches"  "releases.edn"]
            ["11" "batches"  "releases-artists.edn"]])

(defn format-content [type m]
  (case type
    "batches" (:data m)
    "entities" (into [] (vals m))))

(doseq [[id type file] files]
  ;; Ignoring larger batches for simplicity.
  (let [content (edn/read-string (get-url (str base-url type "/" file)))]
    (write-edn (format-content type content)
               (str base-path id "-" file))))

(System/exit 0)
