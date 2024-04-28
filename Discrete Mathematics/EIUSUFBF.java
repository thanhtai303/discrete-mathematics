import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

public class EIUSUFBF {
	static InputReader reader;

	public static void main(String[] args) throws IOException {
		reader = new InputReader(System.in);
		Account[] accountList = makeGraph();
		Output(accountList);
	}

	public static Account[] makeGraph() {
		int numberOfAccount = reader.nextInt();
		int numberOfRelationship = reader.nextInt();
		int minimunNumberOfFriend = reader.nextInt();
		Account[] accountList = new Account[numberOfAccount];

		for (int i = 0; i < accountList.length; i++) {
			accountList[i] = new Account(i);
		}
		for (int i = 0; i < numberOfRelationship; i++) {
			int a = reader.nextInt();
			int b = reader.nextInt();
			accountList[a].addFriend(accountList[b]);
			accountList[b].addFriend(accountList[a]);
		}
		for (int i = 0; i < accountList.length; i++) {
			accountList[i].friendList.sort((f1, f2) -> Integer.compare(f1.id, f2.id));
		}
		for (int i = 0; i < accountList.length; i++) {
			accountList[i].getListFriendShouldSuggest(minimunNumberOfFriend);
		}
		return accountList;
	}

	public static void Output(Account[] accountList) {
		StringBuilder sb = new StringBuilder();
		for (Account i : accountList) {
			sb.append(i).append("\n");
		}
		System.out.println(sb);
	}

	static class Account {
		public int id;
		public List<Account> friendList = new ArrayList<Account>();
		public List<Account> suggestList = new ArrayList<Account>();

		public Account(int id) {
			this.id = id;
		}

		public void addFriend(Account acc) {
			this.friendList.add(acc);
		}

		public void getListFriendShouldSuggest(int minimunNumberOfFriend) {
			for (Account i : this.friendList) {
				if (i.friendList.size() < minimunNumberOfFriend) {
					suggestList.add(i);
				}
			}
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append(this.id).append(" ");
			for (Account i : this.suggestList) {
				sb.append(i.id).append(" ");
			}
			return sb.toString();
		}
	}

	static class InputReader {
		private byte[] inbuf = new byte[2 << 23];
		public int lenbuf = 0, ptrbuf = 0;
		public InputStream is;

		public InputReader(InputStream stream) throws IOException {

			inbuf = new byte[2 << 23];
			lenbuf = 0;
			ptrbuf = 0;
			is = System.in;
			lenbuf = is.read(inbuf);
		}

		public InputReader(FileInputStream stream) throws IOException {
			inbuf = new byte[2 << 23];
			lenbuf = 0;
			ptrbuf = 0;
			is = stream;
			lenbuf = is.read(inbuf);
		}

		public boolean hasNext() throws IOException {
			if (skip() >= 0) {
				ptrbuf--;
				return true;
			}
			return false;
		}

		public String nextLine() throws IOException {
			int b = skip();
			StringBuilder sb = new StringBuilder();
			while (!isSpaceChar(b) && b != ' ') { // when nextLine, ()
				sb.appendCodePoint(b);
				b = readByte();
			}
			return sb.toString();
		}

		public String next() {
			int b = skip();
			StringBuilder sb = new StringBuilder();
			while (!(isSpaceChar(b))) { // when nextLine, (isSpaceChar(b) && b
										// != ' ')
				sb.appendCodePoint(b);
				b = readByte();
			}
			return sb.toString();
		}

		private int readByte() {
			if (lenbuf == -1)
				throw new InputMismatchException();
			if (ptrbuf >= lenbuf) {
				ptrbuf = 0;
				try {
					lenbuf = is.read(inbuf);
				} catch (IOException e) {
					throw new InputMismatchException();
				}
				if (lenbuf <= 0)
					return -1;
			}
			return inbuf[ptrbuf++];
		}

		private boolean isSpaceChar(int c) {
			return !(c >= 33 && c <= 126);
		}

		private double nextDouble() {
			return Double.parseDouble(next());
		}

		public Character nextChar() {
			return skip() >= 0 ? (char) skip() : null;
		}

		private int skip() {
			int b;
			while ((b = readByte()) != -1 && isSpaceChar(b))
				;
			return b;
		}

		public int nextInt() {
			int num = 0, b;
			boolean minus = false;
			while ((b = readByte()) != -1 && !((b >= '0' && b <= '9') || b == '-'))
				;
			if (b == '-') {
				minus = true;
				b = readByte();
			}

			while (true) {
				if (b >= '0' && b <= '9') {
					num = num * 10 + (b - '0');
				} else {
					return minus ? -num : num;
				}
				b = readByte();
			}
		}

		public long nextLong() {
			long num = 0;
			int b;
			boolean minus = false;
			while ((b = readByte()) != -1 && !((b >= '0' && b <= '9') || b == '-'))
				;
			if (b == '-') {
				minus = true;
				b = readByte();
			}

			while (true) {
				if (b >= '0' && b <= '9') {
					num = num * 10 + (b - '0');
				} else {
					return minus ? -num : num;
				}
				b = readByte();
			}
		}
	}
}
