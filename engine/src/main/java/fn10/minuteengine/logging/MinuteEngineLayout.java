package fn10.minuteengine.logging;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Node;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.config.plugins.processor.PluginProcessor;
import org.apache.logging.log4j.core.layout.AbstractStringLayout;
import org.apache.logging.log4j.Level;

@Plugin(name = "MinuteEngineLayout", category = Node.CATEGORY, printObject = true)
public class MinuteEngineLayout extends AbstractStringLayout {

    protected MinuteEngineLayout(Charset charset) {
        super(charset);
    }

    public static final String Black = "\u001B[0;90m";
    public static final String Red = "\u001B[0;91m";
    public static final String Green = "\u001B[0;92m";
    public static final String Yellow = "\u001B[0;93m";
    public static final String Blue = "\u001B[0;94m";
    public static final String Purple = "\u001B[0;95m";
    public static final String Cyan = "\u001B[0;96m";
    public static final String White = "\u001B[0;97m";
    public static final String Reset = "\u001B[0m";

    @Override
    public String toSerializable(LogEvent event) {
        StringBuilder builder = new StringBuilder();
        if (event.getLevel().isInRange(Level.INFO, Level.ALL)) {
            builder.append(Purple);
        } else if (event.getLevel().isInRange(Level.WARN, Level.ALL)) {
            builder.append(Yellow);
        } else if (event.getLevel().isInRange(Level.ERROR, Level.ALL)) {
            builder.append(Red);
        }

        builder.append("(");
        builder.append(event.getSource().getClassName());
        builder.append(" @ ");
        builder.append(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SS")
                .format(Date.from(Instant.ofEpochMilli(event.getInstant().getEpochMillisecond()))));
        builder.append(") ");
        builder.append("[");
        builder.append(event.getLevel().toString());
        builder.append("] : ");
        if (event.getLevel().isInRange(Level.INFO, Level.ALL))
            builder.append(Reset);
        builder.append(event.getMessage().getFormattedMessage());
        builder.append(Reset);
        builder.append("\n");
        return builder.toString();
    }

    @PluginFactory
    public static MinuteEngineLayout createLayout() {
        return new MinuteEngineLayout(Charset.defaultCharset());
    }

}
