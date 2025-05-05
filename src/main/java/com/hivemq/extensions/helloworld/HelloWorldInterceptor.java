/*
 * Copyright 2018-present HiveMQ GmbH
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
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hivemq.extensions.helloworld;

import com.hivemq.extension.sdk.api.annotations.NotNull;
import com.hivemq.extension.sdk.api.interceptor.publish.PublishOutboundInterceptor;
import com.hivemq.extension.sdk.api.interceptor.publish.parameter.PublishOutboundInput;
import com.hivemq.extension.sdk.api.interceptor.publish.parameter.PublishOutboundOutput;
import com.hivemq.extension.sdk.api.packets.publish.ModifiablePublishPacket;
import com.hivemq.extension.sdk.api.packets.publish.PublishPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * This is a very simple {@link PublishOutboundInterceptor},
 * it logs the target clientId,topic,qos,retained of every outgoing PUBLISH.
 *
 * @author Dasha Samkova
 * @since 4.39.0
 */
public class HelloWorldInterceptor implements PublishOutboundInterceptor {
    private static final @NotNull Logger log = LoggerFactory.getLogger(HelloWorldInterceptor.class);
    @Override
    public void onOutboundPublish(
            final @NotNull PublishOutboundInput publishOutboundInput,
            final @NotNull PublishOutboundOutput publishOutboundOutput) {

        final PublishPacket publishPacket = publishOutboundInput.getPublishPacket();
        final String topic = publishPacket.getTopic();
        final Integer qosNumber = publishPacket.getQos().getQosNumber();
        final String clientId = publishOutboundInput.getClientInformation().getClientId();
        log.info("Intercepted an outbound publish packet to clientId: {}, topic: {}, qos: {}", clientId, topic, qosNumber);
    }
}