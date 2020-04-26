import React, { Component } from 'react'
import { Grid, Paper, Typography } from '@material-ui/core'



export default class CourseInfo extends Component {
    render() {

        const { description, name, semester } = this.props.course

        return (
            <React.Fragment>
                <Paper elevation={10}>
                    <Grid container direction="column" justify='center' spacing={2} alignItems="stretch">
                        <Grid item>
                            <Grid container direction="row" justify='center' spacing={1}>
                                <Grid item>
                                    <Grid spacing={2} container direction="column" justify='center' alignItems="center">
                                        <Grid item md={2}>  <Typography variant="h5"> Opis </Typography> </Grid>
                                        <Grid item md={2}>  <Typography variant="h5"> Ime </Typography> </Grid>
                                        <Grid item md={2}>  <Typography variant="h5"> Semestar </Typography> </Grid>

                                    </Grid>
                                </Grid>
                                <Grid item>
                                    <Grid container spacing={2} direction="column" justify="center" alignItems="center">
                                        <Grid item md={2}>  <Typography variant="h6"> {description} </Typography> </Grid>
                                        <Grid item md={2}>  <Typography variant="h6"> {name} </Typography> </Grid>
                                        <Grid item md={2}>  <Typography variant="h6"> {semester} </Typography> </Grid>

                                    </Grid>
                                </Grid>

                            </Grid>
                        </Grid>

                    </Grid>
                </Paper>
            </React.Fragment>
        )
    }
}
