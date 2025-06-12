## Linear Scaling

- Either Forward Or Lateral Tuner

### Forward Tuning

- Align Ruler Alongside Robot
- Push Robot 48 Inches Forward
- Observe Tuner Outputs
- First Num  : Distance Robot Estimated
- Second Num : Linear Scalar To Update

### Lateral Tuning

- Align Ruler Alongside Robot
- Push Robot 48 Inches To The Right
- First Num  : Distance Robot Estimated
- Second Num : Linear Scalar To Update

- Add the linear scalar value in the LConstants block

```java
OTOSConstants.linearScalar = X;
```

## Angular Scaling

- Place Robot So It Faces Reference Point
- Run `Turn Localizer Tuner`
- Rotate Robot One Full Rotation
- First Number  : Distance Robot Estimated
- Second Number : Angular Scalar To Update
- Replace angular scalar in the LConstants block
