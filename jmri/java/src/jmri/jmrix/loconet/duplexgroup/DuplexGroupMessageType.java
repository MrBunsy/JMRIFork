/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jmri.jmrix.loconet.duplexgroup;

/**
 *
 * @author B. Milhaupt Copyright 2011
 * @version			$Revision: 1.0 $
 */
public enum DuplexGroupMessageType {
        NOT_A_DUPLEX_GROUP_MESSAGE,
        DUPLEX_GROUP_NAME_ETC_REPORT_MESSAGE,
        DUPLEX_GROUP_NAME_QUERY_MESSAGE,
        DUPLEX_GROUP_NAME_WRITE_MESSAGE,
        DUPLEX_GROUP_PASSWORD_REPORT_MESSAGE,
        DUPLEX_GROUP_PASSWORD_QUERY_MESSAGE,
        DUPLEX_GROUP_PASSWORD_WRITE_MESSAGE,
        DUPLEX_GROUP_CHANNEL_REPORT_MESSAGE,
        DUPLEX_GROUP_CHANNEL_QUERY_MESSAGE,
        DUPLEX_GROUP_CHANNEL_WRITE_MESSAGE,
        DUPLEX_GROUP_ID_REPORT_MESSAGE,
        DUPLEX_GROUP_ID_QUERY_MESSAGE,
        DUPLEX_GROUP_ID_WRITE_MESSAGE
                ;

}