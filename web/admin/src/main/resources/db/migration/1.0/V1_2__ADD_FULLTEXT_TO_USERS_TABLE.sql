ALTER TABLE `users`
ADD FULLTEXT `FULLNAME_IDX` (first_name, last_name);