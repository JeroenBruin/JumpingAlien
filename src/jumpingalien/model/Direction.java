package jumpingalien.model;

public enum Direction {
	RIGHT {
		public char getSymbol() {
			return 'R';
		}
	}
	LEFT {
		public char getSymbol() {
			return 'L';
		}
	}
}
