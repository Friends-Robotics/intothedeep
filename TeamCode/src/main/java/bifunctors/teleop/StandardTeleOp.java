package bifunctors.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import bifunctors.helper.IntakeMotorStates;
import bifunctors.helper.ViperSlideDirections;
import bifunctors.hardwaremap.RobotHardware;

@TeleOp(name="StandardTeleOp", group="Y13CompCode/FSL")
public class StandardTeleOp extends LinearOpMode {

    // Create a RobotHardware object to be used to access robot hardware.
    // Prefix any hardware functions with "robot." to access this class.
    RobotHardware robot = new RobotHardware(this);
    double slowModeMultiplier = 0;
    boolean clawClosed = true;
    ViperSlideDirections viperSlideMovement = ViperSlideDirections.NONE;
    IntakeMotorStates intakeMotorMovement = IntakeMotorStates.NONE;
    boolean facingForward = true;
    boolean drawerSlideOut = false;
    boolean flipMotorOut = false;
    int desiredTicks = 0;
    boolean manualViperControl = true;
    private boolean finalFoldUsed = false;


    @Override
    public void runOpMode()
    {
        robot.init(true);
        waitForStart();

        while (opModeIsActive()) {
            ReceiveInput();
            ApplyInput();
            SendTelemetry();

            sleep(20);
        }
    }

    protected void ReceiveInput(){
        //Getting desired slow mode:
        if(gamepad1.left_trigger > 0.2){
            slowModeMultiplier = 0.7;
        }
        else if(gamepad1.right_trigger > 0.2) {
            slowModeMultiplier = 0.2;
        }
        else{
            slowModeMultiplier = 0.4;
        }

        if(gamepad1.ps){
            facingForward = !facingForward;
            gamepad1.setLedColor(1,0.4,0, Gamepad.LED_DURATION_CONTINUOUS);
            robot.FlipDTDirection(facingForward);
        }
        else if(gamepad1.touchpad){
            facingForward = false;
            gamepad1.setLedColor(0,0,1, Gamepad.LED_DURATION_CONTINUOUS);
            robot.FlipDTDirection(facingForward);
        }

        //Getting desired claw position
        if(gamepad1.cross){
            clawClosed = true;
        }
        else if(gamepad1.circle){
            clawClosed = false;
        }

        //Getting viper slide directions
        if(gamepad1.right_bumper){
            viperSlideMovement = ViperSlideDirections.UPWARDS;
            manualViperControl = true;
        }
        else if (gamepad1.left_bumper) {
            viperSlideMovement = ViperSlideDirections.DOWNWARDS;
            manualViperControl = true;
        }
        else{
            viperSlideMovement = ViperSlideDirections.NONE;
        }

        if(gamepad2.dpad_up){
            drawerSlideOut = true;
            flipMotorOut = true;
        }
        else if(gamepad2.dpad_down){
            drawerSlideOut = false;
            flipMotorOut = false;
        }

        if(gamepad2.square){
            drawerSlideOut = true;
        }
        else if(gamepad2.cross){
            drawerSlideOut = false;
        }

        if(gamepad2.touchpad){
            finalFoldUsed = true;
        }
        else if(gamepad2.ps){
            finalFoldUsed = false;
        }

        if(gamepad2.triangle){
            flipMotorOut = true;
        }
        else if(gamepad2.circle){
            flipMotorOut = false;
        }

        if(gamepad1.dpad_up){
            desiredTicks = RobotHardware.TopRungEncoders;
            manualViperControl = false;
        }
        else if(gamepad1.dpad_down){
            desiredTicks = RobotHardware.BottomEncoders;
            manualViperControl = false;
        }

        if(gamepad2.right_bumper){
            intakeMotorMovement = IntakeMotorStates.IN;
        }
        else if(gamepad2.left_bumper){
            intakeMotorMovement = IntakeMotorStates.OUT;
        }
        else{
            intakeMotorMovement = IntakeMotorStates.NONE;
        }
    }

    protected void ApplyInput(){
        if(manualViperControl){
            robot.SetViperSlideMovement(viperSlideMovement);
        }
        else{
            robot.SetViperSlidePos(desiredTicks);
        }

        if(!finalFoldUsed){
            robot.IntakeSystem(drawerSlideOut, flipMotorOut);
        }
        else{
            robot.FinalFold();
        }
        robot.SetIntakeMotorMovement(intakeMotorMovement);
        if(facingForward){
            robot.DriveTrain(slowModeMultiplier, -gamepad1.left_stick_y, gamepad1.left_stick_x, -gamepad1.right_stick_x);
        }
        else{
            robot.DriveTrain(slowModeMultiplier, -gamepad1.left_stick_y, gamepad1.left_stick_x, -gamepad1.right_stick_x);
        }
        robot.SetClawPos(clawClosed);
    }

    protected void SendTelemetry(){
        telemetry.addLine("POSITIONS/DIRECTIONS")
                .addData("\nSLOW MODE MULTIPLIER: ", slowModeMultiplier);
        telemetry.update();
    }
}
