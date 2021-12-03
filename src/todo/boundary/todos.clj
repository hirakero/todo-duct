(ns todo.boundary.todos
  (:require [clojure.java.jdbc :as jdbc]
            [honey.sql :as sql]
            [honey.sql.helpers :refer [select from where insert-into update]]))

(defprotocol Todos
  (get-todos [db])
  (fetch-todo [db id])
  (create-todo [db params])
  (update-todo [db id params])
  (delete-todo [db id]))

(extend-protocol Todos
  duct.database.sql.Boundary

  (get-todos [{:keys [spec]}]
    (jdbc/query spec (sql/format {:select :* :from :todos})))

  (fetch-todo [{:keys [spec]} id]
    (jdbc/query spec (sql/format {:select :*
                                  :from :todos
                                  :where [:= :id id]})))
  #_(let [id 1]
    [(format "SELECT * FROM todos WHERE id = '%s'" id)]    
    (sql/format {:select :*
                 :from :todos
                 :where [:= :id id]})
    (sql/format (-> (select :*)
                    (from :todos)
                    (where [:= :id id]))))

  (create-todo [{:keys [spec]} params]
    (jdbc/insert! spec :todos {:title (:title params)}))

  #_(let [id 1, params {:title "test"}]
      (sql/format {:insert-into :todos
                   :values [{:title (:title params)}]})
      (sql/format {:insert-into :todos
                   :columns [:title]
                   :values [[(:title params)]]}))
  (update-todo [{:keys [spec]} id params]
    (jdbc/insert! spec :todos {:title (:title params)} ["id = ?" id]))

  #_(let [id 1, params {:title "test"}]
      (sql/format {:update :todos
                   :set {:title (:title params)}
                   :where [:= :id id]}))

  (delete-todo [{:keys [spec]} id]
    (jdbc/delete! spec :todos ["id = ?" id]))
  )
