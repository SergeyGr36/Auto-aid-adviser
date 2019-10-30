package com.hillel.evo.adviser.parameters;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MessageParameters {
    private String toAddresses;
    private String ccAddresses;
    private String bccAddresses;

    private String subject;
    private String text;
    private String html;

    @SuppressWarnings("PMD.BeanMembersShouldSerialize")
    public static class Builder {
        private String toAddresses;
        private String ccAddresses;
        private String bccAddresses;
        private String subject;
        private String text;
        private String html;

        public Builder setToAddresses(String toAddresses) {
            this.toAddresses = toAddresses;
            return this;
        }

        public Builder setCcAddresses(String ccAddresses) {
            this.ccAddresses = ccAddresses;
            return this;
        }

        public Builder setBccAddresses(String bccAddresses) {
            this.bccAddresses = bccAddresses;
            return this;
        }

        public Builder setSubject(String subject) {
            this.subject = subject;
            return this;
        }

        public Builder setText(String text) {
            this.text = text;
            return this;
        }

        public Builder setHtml(String html) {
            this.html = html;
            return this;
        }

        public MessageParameters build() {
            return new MessageParameters(toAddresses, ccAddresses, bccAddresses, subject, text, html);
        }
    }
}
