/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2006, 2007, 2009, 2010 Zimbra, Inc.
 * 
 * The contents of this file are subject to the Zimbra Public License
 * Version 1.3 ("License"); you may not use this file except in
 * compliance with the License.  You may obtain a copy of the License at
 * http://www.zimbra.com/license.
 * 
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied.
 * ***** END LICENSE BLOCK *****
 */
package org.jivesoftware.wildfire.muc;

import org.jivesoftware.util.NotFoundException;
import org.jivesoftware.wildfire.ChannelHandler;
import org.xmpp.packet.JID;

import java.util.Iterator;

/**
 * The chat user is a separate user abstraction for interacting with
 * the chat server. Centralizing chat users to the Jabber entity that
 * sends and receives the chat messages allows us to create quality of
 * service, authorization, and resource decisions on a real-user basis.
 * <p/>
 * Most chat users in a typical s2s scenario will not be local users.
 * </p><p>
 * MUCUsers play one or more roles in one or more chat rooms on the
 * server.
 *
 * @author Gaston Dombiak
 */
public interface MUCUser extends ChannelHandler {

    /**
     * Obtain a user ID (useful for database indexing).
     *
     * @return The user's id number if any (-1 indicates the implementation doesn't support ids)
     */
    long getID();

    /**
      * Obtain the address of the user. The address is used by services like the core
      * server packet router to determine if a packet should be sent to the handler.
      * Handlers that are working on behalf of the server should use the generic server
      * hostname address (e.g. server.com).
      *
      * @return the address of the packet handler.
      */
     public JID getAddress();

    /**
     * Obtain the role of the user in a particular room.
     *
     * @param roomName The name of the room we're interested in
     * @return The role the user plays in that room
     * @throws NotFoundException     if the user does not have a role in the given room
     */
    MUCRole getRole(String roomName) throws NotFoundException;

    /**
     * Get all roles for this user.
     *
     * @return Iterator over all roles for this user
     */
    Iterator<MUCRole> getRoles();

    /**
     * Adds the role of the user in a particular room.
     *
     * @param roomName The name of the room.
     * @param role The new role of the user.
     */
    void addRole(String roomName, MUCRole role);

    /**
     * Removes the role of the user in a particular room.<p>
     *
     * Note: PREREQUISITE: A lock on this object has already been obtained.
     *
     * @param roomName The name of the room we're being removed
     */
    void removeRole(String roomName);

    /**
     * Returns true if the user is currently present in one or more rooms.
     *
     * @return true if the user is currently present in one or more rooms.
     */
    boolean isJoined();

    /**
     * Get time (in milliseconds from System currentTimeMillis()) since last packet.
     *
     * @return The time when the last packet was sent from this user
     */
    long getLastPacketTime();
}