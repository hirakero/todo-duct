(ns todo.handler.todos
  (:require [ataraxy.response :as response]
            [integrant.core :as ig]))

(defmethod ig/init-key ::list [_ _] ; ::list = todo.handlers.todos/list
  (fn [_]
    [::response/ok {:message "OK!!"}]))
