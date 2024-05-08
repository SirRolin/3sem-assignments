package sir.rolin.my_library.utils;

public interface KeyValueSupplier<K, T> {
    K getKey();
    T getValue();
}
