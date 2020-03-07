(ns appliedsciencestudio.covid19-clj-viz.korea
  (:require [clojure.data.csv :as csv]
            [clojure.string :as string]))

(def normalize-sido
  {"울산" "울산광역시"
   "강원" "강원도"
   "전북" "전라북도"
   "대구" "대구광역시"
   "대전" "대전광역시"
   "세종" "세종특별자치시"
   "인천" "인천광역시"
   "경남" "경상남도"
   "전남" "전라남도"
   "경기" "경기도"
   "충북" "충청북도"
   "제주" "제주특별자치도"
   "서울" "서울특별시"
   "충남" "충청남도"
   "부산" "부산광역시"
   "광주" "광주광역시"
   "경북" "경상북도"})

(def cases
  (->> (csv/read-csv (slurp "resources/korea.covid19cases.tsv")
                     :separator \tab)
       (rest)
       (reduce (fn [acc [name & vars]]
                 (assoc acc (normalize-sido name)
                            (zipmap [:day_diff :confirmed :death :rate :day_inspect]
                                    (map read-string vars))))
               {})))


(def population
   (->> (csv/read-csv (slurp "resources/korea.province-population.tsv")
                      :separator \tab)
        (rest)
        (reduce (fn [acc [name population density]]
                  (assoc acc (normalize-sido name)
                             (* (read-string population) 1000)))
                {})))
