package ca.jrvs.apps.grep.practice;

public interface RegexExc {



        /**
         * Return true if filename extension is .jpg or .jpeg (case insensitive)
         * @param filename
         * @return boolean
         */
        public boolean matchJpeg(String filename);

        /**
         * Return true if IP is valid
         * @param ip
         * @return boolean
         */
        public boolean matchIP(String ip);

        /**
         * Return true if line is empty
         * @param line
         * @return boolean
         */
        public boolean isLineEmpty(String line);
    }

