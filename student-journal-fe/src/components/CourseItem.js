import React, { Component } from 'react';
import PropType from 'prop-types';
import Card from '@material-ui/core/Card'
import Typography from '@material-ui/core/Typography'
import CssBaseline from '@material-ui/core/CssBaseline';
import Grid from '@material-ui/core/Grid'

export class CourseItem extends Component {
    render() {

        const { name, semester, description, courseID } = this.props.course;

        return (
            <React.Fragment>
                <CssBaseline />
                <Card onClick={this.props.clicked.bind(this, courseID)}>
                    <Grid container justify='center' direction='row' align-items='center'>
                        <Grid item>
                            <Grid container justify="center" direction="row">
                                <Grid item>
                                    <Typography color='secondary' variant="h4" gutterBottom>
                                        Ime kursa
                                    </Typography>
                                </Grid>
                            </Grid>
                            <Grid item>
                                <Grid container justify="center" direction="row">
                                    <Grid item>
                                        <Typography variant="h6" gutterBottom>
                                            {name}
                                        </Typography>
                                    </Grid>
                                </Grid>
                            </Grid>
                            <Grid item>
                                <Grid container justify="center" direction="row">
                                    <Grid item>
                                        <Typography color='secondary' variant="h4" gutterBottom>
                                            Semestar
                                    </Typography>
                                    </Grid>
                                </Grid>
                            </Grid>

                            <Grid item>
                                <Grid container justify="center" direction="row">
                                    <Grid item>
                                        <Typography variant="h6" gutterBottom>
                                            {semester}
                                        </Typography>
                                    </Grid>
                                </Grid>
                            </Grid>
                            <Grid item>
                                <Grid container justify="center" direction="row">
                                    <Grid item>
                                        <Typography color='secondary' variant="h4" gutterBottom>
                                            Opis
                    </Typography >
                                    </Grid>
                                </Grid>
                            </Grid>
                            <Grid item>
                                <Grid container justify="center" direction="row">
                                    <Grid item>
                                        <Typography variant="h5" gutterBottom>
                                            {description}
                                        </Typography >
                                    </Grid>
                                </Grid>
                            </Grid>
                        </Grid>

                    </Grid>
                </Card>
            </React.Fragment >
        )
    }
}

CourseItem.propType = {
    obligation: PropType.object.isRequired
}



export default CourseItem
