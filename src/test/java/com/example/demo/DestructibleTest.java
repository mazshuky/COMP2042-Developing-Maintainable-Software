package com.example.demo;

import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DestructibleTest {

    private Destructible destructible;

    @BeforeEach
    void setUp() {
        destructible = mock(Destructible.class);
    }

    @Test
    void testTakeDamage() {
        destructible.takeDamage();
        verify(destructible, times(1)).takeDamage();
    }

    @Test
    void testDestroy() {
        destructible.destroy();
        verify(destructible, times(1)).destroy();
    }
}
