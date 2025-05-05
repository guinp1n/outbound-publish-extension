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
import com.hivemq.extension.sdk.api.interceptor.publish.parameter.PublishInboundInput;
import com.hivemq.extension.sdk.api.interceptor.publish.parameter.PublishInboundOutput;
import com.hivemq.extension.sdk.api.interceptor.publish.parameter.PublishOutboundInput;
import com.hivemq.extension.sdk.api.interceptor.publish.parameter.PublishOutboundOutput;
import com.hivemq.extension.sdk.api.packets.publish.ModifiablePublishPacket;
import com.hivemq.extension.sdk.api.packets.publish.PublishPacket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * @author Yannick Weber
 */
class HelloWorldInterceptorTest {

    private @NotNull HelloWorldInterceptor helloWorldInterceptor;
    private @NotNull PublishOutboundInput publishOutboundInput;
    private @NotNull PublishOutboundOutput publishOutboundOutput;
    private @NotNull PublishPacket publishPacket;

    @BeforeEach
    void setUp() {
        helloWorldInterceptor = new HelloWorldInterceptor();
        publishOutboundInput = mock(PublishOutboundInput.class);
        publishOutboundOutput = mock(PublishOutboundOutput.class);
        publishPacket = mock(PublishPacket.class);
        when(publishOutboundInput.getPublishPacket()).thenReturn(publishPacket);
    }

    @Test
    void topicHelloWorld_payloadModified() {
        when(publishPacket.getTopic()).thenReturn("hello/world");
        helloWorldInterceptor.onOutboundPublish(publishOutboundInput,publishOutboundOutput);
        final ArgumentCaptor<ByteBuffer> captor = ArgumentCaptor.forClass(ByteBuffer.class);
        verify(publishPacket).getPayload();
        assertEquals("Hello World!", new String(captor.getValue().array(), StandardCharsets.UTF_8));
    }

    @Test
    void topicNotHelloWorld_payloadNotModified() {
        when(publishPacket.getTopic()).thenReturn("some/topic");
        helloWorldInterceptor.onOutboundPublish(publishOutboundInput, publishOutboundOutput);
        verify(publishPacket, times(0)).getPayload();
    }
}