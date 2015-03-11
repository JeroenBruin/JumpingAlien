package jumpingalien.model;

public enum XState {
	STILL {
		public char getSymbol() {
			return 'S';
		}
	},
	ACCELERATING {
		public char getSymbol() {
			return 'A';
		}
	},
	MAXED_OUT {
		public char getSymbol() {
			return 'M';
		}
	};
	
	public abstract char getSymbol();

}
