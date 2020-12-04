package util;

public class SymKeyStringTuple {


        public String key; // encryption key public key of the client
        public String message; // verified data , encrypted using the pub

        public String getKey() {
                return key;
        }

        public void setKey(String key) {
                this.key = key;
        }

        public String getMessage() {
                return message;
        }

        public void setMessage(String message) {
                this.message = message;
        }
}
