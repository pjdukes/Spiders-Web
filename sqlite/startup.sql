CREATE TABLE Domain(name String PRIMARY KEY, protocol text);
CREATE TABLE Path(domain text, path text, data text, FOREIGN KEY (domain) REFERENCES Domain(name));