package com.pocketchat.models.otp;

public class VerifyOTPNumberResponse {
    private boolean correct;
    private boolean hasError;
    private Integer limitRemaining;

    VerifyOTPNumberResponse(boolean correct, boolean hasError, Integer limitRemaining) {
        this.correct = correct;
        this.hasError = hasError;
        this.limitRemaining = limitRemaining;
    }

    public static VerifyOTPNumberResponseBuilder builder() {
        return new VerifyOTPNumberResponseBuilder();
    }


    public boolean isCorrect() {
        return this.correct;
    }

    public boolean isHasError() {
        return this.hasError;
    }

    public Integer getLimitRemaining() {
        return this.limitRemaining;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }

    public void setLimitRemaining(Integer limitRemaining) {
        this.limitRemaining = limitRemaining;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof VerifyOTPNumberResponse)) return false;
        final VerifyOTPNumberResponse other = (VerifyOTPNumberResponse) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.isCorrect() != other.isCorrect()) return false;
        if (this.isHasError() != other.isHasError()) return false;
        final Object this$limitRemaining = this.getLimitRemaining();
        final Object other$limitRemaining = other.getLimitRemaining();
        if (this$limitRemaining == null ? other$limitRemaining != null : !this$limitRemaining.equals(other$limitRemaining))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof VerifyOTPNumberResponse;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + (this.isCorrect() ? 79 : 97);
        result = result * PRIME + (this.isHasError() ? 79 : 97);
        final Object $limitRemaining = this.getLimitRemaining();
        result = result * PRIME + ($limitRemaining == null ? 43 : $limitRemaining.hashCode());
        return result;
    }

    public String toString() {
        return "VerifyOTPNumberResponse(correct=" + this.isCorrect() + ", hasError=" + this.isHasError() + ", limitRemaining=" + this.getLimitRemaining() + ")";
    }

    public static class VerifyOTPNumberResponseBuilder {
        private boolean correct;
        private boolean hasError;
        private Integer limitRemaining;

        VerifyOTPNumberResponseBuilder() {
        }

        public VerifyOTPNumberResponse.VerifyOTPNumberResponseBuilder correct(boolean correct) {
            this.correct = correct;
            return this;
        }

        public VerifyOTPNumberResponse.VerifyOTPNumberResponseBuilder hasError(boolean hasError) {
            this.hasError = hasError;
            return this;
        }

        public VerifyOTPNumberResponse.VerifyOTPNumberResponseBuilder limitRemaining(Integer limitRemaining) {
            this.limitRemaining = limitRemaining;
            return this;
        }

        public VerifyOTPNumberResponse build() {
            return new VerifyOTPNumberResponse(correct, hasError, limitRemaining);
        }

        public String toString() {
            return "VerifyOTPNumberResponse.VerifyOTPNumberResponseBuilder(correct=" + this.correct + ", hasError=" + this.hasError + ", limitRemaining=" + this.limitRemaining + ")";
        }
    }
}
