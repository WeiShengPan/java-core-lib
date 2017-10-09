package javafeatures.typeinfo.test;

/**
 * 空对象
 *
 * @author panws
 * @since 2017-09-30
 */
public class NullObject {

}

interface Null {

}

class Person {

	public static final Person NULL = new NullPerson();    //空对象的单例

	public final String first;

	public final String last;

	public final String address;

	public Person(String first, String last, String address) {
		this.first = first;
		this.last = last;
		this.address = address;
	}

	@Override public String toString() {
		return "Person: " + first + " " + last + ". Address: " + address;
	}

	/**
	 * 空对象
	 */
	public static class NullPerson extends Person implements Null {

		public NullPerson() {
			super("None", "None", "None");
		}

		@Override public String toString() {
			return "Person: NullPerson";
		}
	}
}

class Position {

	private String title;

	private Person person;

	public Position(String title, Person employee) {
		this.title = title;
		this.person = employee;
		if (person == null) {
			person = Person.NULL;
		}
	}

	public Position(String title) {
		this.title = title;
		this.person = Person.NULL;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
		if (this.person == null) {
			this.person = Person.NULL;
		}
	}

	@Override public String toString() {
		return "Position: " + title + " " + person;
	}
}