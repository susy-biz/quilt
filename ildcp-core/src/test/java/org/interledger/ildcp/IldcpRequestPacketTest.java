package org.interledger.ildcp;

/*-
 * ========================LICENSE_START=================================
 * Interledger DCP Core
 * %%
 * Copyright (C) 2017 - 2019 Hyperledger and its contributors
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * =========================LICENSE_END==================================
 */

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import org.junit.Test;

import java.math.BigInteger;
import java.time.Instant;

/**
 * Unit tests for {@link IldcpRequestPacket}.
 */
public class IldcpRequestPacketTest {

  @Test
  public void testBuilder() {
    final IldcpRequestPacket actual = IldcpRequestPacket.builder().build();
    assertThat(actual.getAmount(), is(BigInteger.ZERO));
    assertThat(actual.getDestination(), is(IldcpRequestPacket.PEER_DOT_CONFIG));
    assertThat(actual.getExecutionCondition(), is(IldcpRequestPacket.EXECUTION_CONDITION));
    assertThat(actual.getData(), is(new byte[0]));
  }

  @Test
  public void testBuilderWithCustomExpiry() {
    final Instant expiresAt = Instant.parse("2019-12-25T01:02:03.996Z");
    final IldcpRequestPacket actual = IldcpRequestPacket.builder().expiresAt(expiresAt).build();

    assertThat(actual.getDestination(), is(IldcpRequestPacket.PEER_DOT_CONFIG));
    assertThat(actual.getExpiresAt(), is(expiresAt));
    assertThat(actual.getAmount(), is(BigInteger.ZERO));
    assertThat(actual.getExecutionCondition(), is(IldcpRequestPacket.EXECUTION_CONDITION));
    assertThat(actual.getData(), is(new byte[0]));
  }

  @Test
  public void testEqualsHashcode() {
    final Instant expiresAt = Instant.parse("2019-12-25T01:02:03.996Z");
    final IldcpRequestPacket first = IldcpRequestPacket.builder().expiresAt(expiresAt).build();
    final IldcpRequestPacket second = IldcpRequestPacket.builder().expiresAt(expiresAt).build();
    final IldcpRequestPacket third = IldcpRequestPacket.builder().amount(BigInteger.TEN).build();

    assertThat(first.equals(second), is(true));
    assertThat(second.equals(first), is(true));
    assertThat(third, is(not(first)));

    assertThat(first.hashCode(), is(second.hashCode()));
    assertThat(second.hashCode(), is(first.hashCode()));
    assertThat(third, is(not(first.hashCode())));
  }

  @Test
  public void testToString() {
    final Instant expiresAt = Instant.parse("2019-12-25T01:02:03.996Z");
    final IldcpRequestPacket first = IldcpRequestPacket.builder().expiresAt(expiresAt).build();

    assertThat(
        first.toString().startsWith(
            "IldcpRequestPacket{destination=InterledgerAddress{value=peer.config}, amount=0, executionCondition="
                + "Condition{hash=Zmh6rfhivXdsj8GLjp+OIAiXFIVu4jOzkCpZHQ1fKSU=}, expiresAt=2019-12-25T01:02:03.996Z,"
                + " data=[B@"),
        is(true)
    );
  }
}
