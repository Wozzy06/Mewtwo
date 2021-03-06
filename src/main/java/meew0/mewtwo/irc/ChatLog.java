package meew0.mewtwo.irc;

import meew0.mewtwo.MewtwoMain;

import java.util.LinkedList;

public class ChatLog {

    public class Message {
        public String nick = "", message = "";
    }

	@SuppressWarnings("WeakerAccess")
    public static final int limit = 500;
	
	@SuppressWarnings("CanBeFinal")
    public LinkedList<Message> messages;

	
	public ChatLog() {
		messages = new LinkedList<>();
	}
	
	public void add(String s, String nick) {
        Message m = new Message();

        m.message = s;
        m.nick = nick;

		messages.addFirst(m);
		
		if(messages.size() > limit) messages.removeLast();
	}

    public Message getLatestFromUser(String nick) {
        for(Message m : messages) {
            if(m.nick.equals(nick)) {
                if(!m.message.startsWith(MewtwoMain.prefix)) {
                    return m;
                }
            }
        }
        return null;
    }

    public Message getLatestThatMatches(String regex) {
        for(Message m : messages) {
            if(m.message.matches(".*" + regex + ".*")) {
                if(!m.message.startsWith(MewtwoMain.prefix)) {
                    return m;
                }
            }
        }
        return null;
    }
}
