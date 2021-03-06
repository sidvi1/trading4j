package de.voidnode.trading4j.domain.marketdata;

import de.voidnode.trading4j.domain.TimeFrame.M1;
import de.voidnode.trading4j.domain.monetary.Price;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Checks if {@link MarketData} works as expected.
 * 
 * @author Raik Bieniek
 */
public class MarketDataTest {

    /**
     * A {@link CandleStick} is only equal to other {@link CandleStick}s that {@link Price} values each are equal.
     */
    @Test
    public void shouldEqualMarketPricesWithSameCloseValue() {
        assertThat(new MarketData<>(1.0)).isEqualTo(new MarketData<>(new Price(1.0)));
        assertThat(new MarketData<>(2.0)).isEqualTo(new ExemplaryMarketPriceSubClass(2.0, 42));
        assertThat(new MarketData<>(1.12345)).isEqualTo(new MarketData<>(1.123449));

        assertThat(new MarketData<>(5.12345)).isNotEqualTo(new MarketData<>(5.12343));
        assertThat(new MarketData<>(1.0)).isNotEqualTo("not a candle stick");
        assertThat(new MarketData<>(1.0)).isNotEqualTo(null);
    }

    /**
     * An exemplary sub-class of a {@link MarketData}.
     */
    private static class ExemplaryMarketPriceSubClass extends MarketData<M1> {
        private final int dummyValue;

        /**
         * Initializes an instance.
         * 
         * @param close
         *            see {@link #getClose()}
         * @param dummyValue
         *            a value without special meaning other than having an extra field in this class.
         */

        ExemplaryMarketPriceSubClass(final double close, final int dummyValue) {
            super(close);
            this.dummyValue = dummyValue;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = super.hashCode();
            result = prime * result + dummyValue;
            return result;
        }

        @Override
        public boolean equals(final Object obj) {
            if (this == obj) {
                return true;
            }
            if (!super.equals(obj)) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final ExemplaryMarketPriceSubClass other = (ExemplaryMarketPriceSubClass) obj;
            if (dummyValue != other.dummyValue) {
                return false;
            }
            return true;
        }
    }
}
