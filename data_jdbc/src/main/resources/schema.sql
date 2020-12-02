DROP TABLE IF EXISTS comment;
DROP TABLE IF EXISTS blog;

CREATE TABLE IF NOT EXISTS blog (
    `blog_id` INT NOT NULL PRIMARY KEY,
    `note` VARCHAR(256) NOT NULL
);

CREATE TABLE IF NOT EXISTS comment (
    `comment_id` INT NOT NULL PRIMARY KEY,
    `note` VARCHAR(256) NOT NULL,
    `blog_id` INT NOT NULL,
    CONSTRAINT fk_comment_blog_id FOREIGN KEY (blog_id) REFERENCES blog(blog_id)
);
