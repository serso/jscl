package jscl;

import javax.annotation.Nonnull;
import org.solovyev.common.msg.Message;
import org.solovyev.common.msg.MessageRegistry;

import java.util.ArrayList;
import java.util.List;

public class FixedCapacityListMessageRegistry implements MessageRegistry {

	@Nonnull
	private final List<Message> messages;

	private final int capacity;

	private volatile int size;

	public FixedCapacityListMessageRegistry(int capacity) {
		this.size = 0;
		this.capacity = capacity;
		this.messages = new ArrayList<Message>(capacity);
	}

	@Override
	public void addMessage(@Nonnull Message message) {
		if (!this.messages.contains(message)) {
			if (this.size <= this.capacity) {
				this.messages.add(message);
				this.size++;
			} else {
				this.messages.remove(0);
				this.messages.add(message);
			}
		}
	}

	@Nonnull
	@Override
	public Message getMessage() {
		if (hasMessage()) {
			this.size--;
			return messages.remove(0);
		} else {
			throw new IllegalStateException("No messages!");
		}
	}

	@Override
	public boolean hasMessage() {
		return size > 0;
	}
}
