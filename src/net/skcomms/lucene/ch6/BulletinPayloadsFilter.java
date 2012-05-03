package net.skcomms.lucene.ch6;

/**
 * Copyright Manning Publications Co.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific lan      
 */

import java.io.IOException;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.PayloadAttribute;
import org.apache.lucene.analysis.tokenattributes.TermAttribute;
import org.apache.lucene.index.Payload;

//import org.apache.lucene.analysis.payloads.PayloadHelper;
// From chapter 6
public class BulletinPayloadsFilter extends TokenFilter {

    private final TermAttribute    termAtt;
    private final PayloadAttribute payloadAttr;
    private boolean                isBulletin;
    private final Payload          boostPayload;

    BulletinPayloadsFilter(TokenStream in, float warningBoost) {
        super(in);
        this.payloadAttr = this.addAttribute(PayloadAttribute.class);
        this.termAtt = this.addAttribute(TermAttribute.class);
        this.boostPayload = new Payload(PayloadHelper.encodeFloat(warningBoost));
    }

    @Override
    public final boolean incrementToken() throws IOException {
        if (this.input.incrementToken()) {
            if (this.isBulletin && this.termAtt.term().equals("warning")) { // #A
                this.payloadAttr.setPayload(this.boostPayload); // #A
            } else {
                this.payloadAttr.setPayload(null); // #B
            }
            return true;
        } else {
            return false;
        }
    }

    void setIsBulletin(boolean v) {
        this.isBulletin = v;
    }
}

/*
 * #A Add payload boost #B Clear payload
 */

