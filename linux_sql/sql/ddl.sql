\c host_agent
CREATE TABLE IF NOT EXISTS PUBLIC.host_info
  (
     id SERIAL NOT NULL,
     hostname VARCHAR NOT NULL,
	 cpu_number INT NOT NULL,
	 cpu_architecture VARCHAR NOT NULL,
	 cpu_model VARCHAR NOT NULL,
	 cpu_mhz FLOAT(3) NOT NULL,
	 L2_cache INT NOT NULL,
	 total_mem INT NOT NULL,
	 "timestamp" TIMESTAMP NOT NULL,
	 PRIMARY KEY (id),
	 UNIQUE(hostname)
  );

CREATE TABLE IF NOT EXISTS PUBLIC.host_usage
  (
     "timestamp" TIMESTAMP NOT NULL,
     host_id SERIAL NOT NULL,
     memory_free INT NOT NULL,
     cpu_idle DECIMAL NOT NULL,
     cpu_kernel DECIMAL NOT NULL,
     disk_io INT NOT NULL,
     disk_available INT NOT NULL,
     CHECK (cpu_idle BETWEEN 0 AND 100),
     CHECK (cpu_kernel BETWEEN 0 AND 100),
     FOREIGN KEY (host_id) REFERENCES host_info(id)
  );
