package com.collegeManagement.app.enums;

public class studentEnum {

    public enum Status {
        INACTIVE(0L),
        ACTIVE(1L);

        private final long value;
        Status(long value) {
            this.value = value;
        }
        }

    public enum gender{
        FEMALE(0l),
        MALE(1l);

        private final long value;
        gender(long value) {
            this.value = value;
        }
    }
}


