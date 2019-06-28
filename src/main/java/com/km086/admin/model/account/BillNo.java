package com.km086.admin.model.account;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.lang.management.ManagementFactory;
import java.net.NetworkInterface;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Enumeration;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Slf4j
public class BillNo implements Serializable {

    private static final int MACHINE_IDENTIFIER;

    private static final short PROCESS_IDENTIFIER;

    private static final int LOW_ORDER_THREE_BYTES = 16777215;

    private static final AtomicInteger NEXT_COUNTER = new AtomicInteger(new SecureRandom().nextInt());

    private static final char[] HEX_CHARS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private static final long serialVersionUID = -5448439004094795956L;

    static {
        try {
            MACHINE_IDENTIFIER = createMachineIdentifier();
            PROCESS_IDENTIFIER = createProcessIdentifier();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @EqualsAndHashCode.Include
    private final int timestamp;

    @EqualsAndHashCode.Include
    private final int machineIdentifier;

    @EqualsAndHashCode.Include
    private final short processIdentifier;

    @EqualsAndHashCode.Include
    private final int counter;

    public BillNo() {
        this(new Date());
    }


    public BillNo(Date date) {
        this(dateToTimestampSeconds(date), MACHINE_IDENTIFIER, PROCESS_IDENTIFIER, NEXT_COUNTER.getAndIncrement(), false);
    }

    private BillNo(int timestamp, int machineIdentifier, short processIdentifier, int counter, boolean checkCounter) {
        if ((machineIdentifier & 0xFF000000) != 0) {
            throw new IllegalArgumentException("The machine identifier must be between 0 and 16777215 (it must fit in three bytes).");
        }

        if ((checkCounter) && ((counter & 0xFF000000) != 0)) {
            throw new IllegalArgumentException("The counter must be between 0 and 16777215 (it must fit in three bytes).");
        }

        this.timestamp = timestamp;
        this.machineIdentifier = machineIdentifier;
        this.processIdentifier = processIdentifier;
        this.counter = (counter & 0xFFFFFF);
    }

    private static byte int3(int x) {
        return (byte) (x >> 24);
    }

    private static byte int2(int x) {
        return (byte) (x >> 16);
    }

    private static byte int1(int x) {
        return (byte) (x >> 8);
    }

    private static byte int0(int x) {
        return (byte) x;
    }

    private static byte short1(short x) {
        return (byte) (x >> 8);
    }

    private static byte short0(short x) {
        return (byte) x;
    }

    private static int dateToTimestampSeconds(Date time) {
        return (int) (time.getTime() / 1000L);
    }

    private static int createMachineIdentifier() {
        int machinePiece;
        try {
            StringBuilder sb = new StringBuilder();
            Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
            while (e.hasMoreElements()) {
                NetworkInterface ni = e.nextElement();
                sb.append(ni.toString());
                byte[] mac = ni.getHardwareAddress();
                if (mac != null) {
                    ByteBuffer bb = ByteBuffer.wrap(mac);
                    try {
                        sb.append(bb.getChar());
                        sb.append(bb.getChar());
                        sb.append(bb.getChar());
                    } catch (BufferUnderflowException localBufferUnderflowException) {
                    }
                }
            }

            machinePiece = sb.toString().hashCode();
        } catch (Throwable t) {
            machinePiece = new SecureRandom().nextInt();
            log.warn("Failed to get machine identifier from network interface, using random number instead", t);
        }
        machinePiece &= 0xFFFFFF;
        return machinePiece;
    }

    private static short createProcessIdentifier() {
        short processId;
        try {
            String processName = ManagementFactory.getRuntimeMXBean().getName();
            if (processName.contains("@")) {
                processId = (short) Integer.parseInt(processName.substring(0, processName.indexOf('@')));
            } else {
                processId = (short) ManagementFactory.getRuntimeMXBean().getName().hashCode();
            }
        } catch (Throwable t) {
            processId = (short) new SecureRandom().nextInt();
            log.warn("Failed to get process identifier from JMX, using random number instead", t);
        }
        return processId;
    }

    public String toHexString() {
        char[] chars = new char[24];
        int i = 0;
        for (byte b : toByteArray()) {
            chars[(i++)] = HEX_CHARS[(b >> 4 & 0xF)];
            chars[(i++)] = HEX_CHARS[(b & 0xF)];
        }
        return new String(chars);
    }

    public byte[] toByteArray() {
        byte[] bytes = new byte[12];
        bytes[0] = int3(this.timestamp);
        bytes[1] = int2(this.timestamp);
        bytes[2] = int1(this.timestamp);
        bytes[3] = int0(this.timestamp);
        bytes[4] = int2(this.machineIdentifier);
        bytes[5] = int1(this.machineIdentifier);
        bytes[6] = int0(this.machineIdentifier);
        bytes[7] = short1(this.processIdentifier);
        bytes[8] = short0(this.processIdentifier);
        bytes[9] = int2(this.counter);
        bytes[10] = int1(this.counter);
        bytes[11] = int0(this.counter);
        return bytes;
    }

    public String toString() {
        return toHexString();
    }
}
