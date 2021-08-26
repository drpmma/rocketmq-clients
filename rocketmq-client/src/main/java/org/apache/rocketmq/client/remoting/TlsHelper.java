/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.rocketmq.client.remoting;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.rocketmq.client.misc.MixAll;
import org.apache.rocketmq.utility.UtilAll;

public class TlsHelper {

    private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";

    private TlsHelper() {
    }

    public static String sign(String accessSecret, String dateTime) throws UnsupportedEncodingException,
                                                                           NoSuchAlgorithmException,
                                                                           InvalidKeyException {
        SecretKeySpec signingKey = new SecretKeySpec(accessSecret.getBytes(MixAll.DEFAULT_CHARSET),
                                                     HMAC_SHA1_ALGORITHM);
        Mac mac;
        mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
        mac.init(signingKey);

        return UtilAll.encodeHexString(mac.doFinal(dateTime.getBytes(MixAll.DEFAULT_CHARSET)), false);
    }
}
