INSERT INTO roles_operations (role_id, op_id)
    SELECT r.id , o.id FROM roles r CROSS JOIN operations o
    WHERE r.name = "Admin";