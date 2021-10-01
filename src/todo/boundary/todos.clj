(ns todo.boundary.todos
  (:require [clojure.java.jdbc :as jdbc]))

(defprotocol Todos
  (get-todos [db]))

(extend-protocol Todos
  duct.database.sql.Boundary

  (get-todos [{:keys [spec]}]
    (jdbc/query spec ["SELECT * FROM todos"])))